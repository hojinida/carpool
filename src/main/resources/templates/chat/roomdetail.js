   // 웹소켓 연결 및 구독 설정
   var socket = new SockJS('/ws');
   var stompClient = Stomp.over(socket);
   var accessToken = localStorage.getItem('accessToken');
   var roomId = window.location.pathname.split('/').pop();

stompClient.connect({
   headers: {
       'Authorization': 'Bearer ' + accessToken
   }
}, function (frame) {
   console.log('Connected: ' + frame);
   console.log('RoomId: ' + roomId);
   stompClient.subscribe('/topic/rooms/' + roomId, function (message) {
       console.log('Received message:', message);
       showMessage(JSON.parse(message.body));
   });
});

function sendWebSocketMessage(message) {
   stompClient.send("/app/rooms/" + roomId + "/chat",
       { 'Authorization': 'Bearer ' + accessToken },
       JSON.stringify(message));
}


   document.getElementById('message-form').addEventListener('submit', function (event) {
   event.preventDefault();
   var content = document.querySelector('input[name="content"]').value;
   if (content.trim() !== '') {
       sendWebSocketMessage({content: content, sender: '사용자 이름'});
       document.querySelector('input[name="content"]').value = '';
   }
});
   document.querySelector('input[name="content"]').addEventListener('keypress', function (event) {
   if (event.key === 'Enter') {
       event.preventDefault();
       document.getElementById('message-form').dispatchEvent(new Event('submit'));
   }
});

function showMessage(message) {
   var messageList = document.getElementById('message-list');
   var messageElement = renderMessage(message);
   messageList.appendChild(messageElement);
   messageList.scrollTop = messageList.scrollHeight;
}

$(document).ready(function() {
   loadMessages(); // 페이지 로드 시 이전 메시지들을 로드합니다.
    // 방을 만든 사용자인지 확인하고, 방 삭제 버튼을 표시합니다.
   checkRoomOwner().then(function(isOwner) {
       if (isOwner) {
           document.getElementById('delete-room-btn').style.display = 'block';
       }
   });
   loadParticipants();
});


function loadMessages() {
   let accessToken = localStorage.getItem('accessToken');
   sendAuthenticatedRequest("/rooms/" + roomId + "/messages", "GET", null, accessToken).then(function (messages) {
       if (messages.length > 0) {
           // 메시지를 역순으로 정렬하여 리스트에 추가합니다.
           messages.forEach(function (message) {
           $("#message-list").prepend(renderMessage(message));
       });
   }
   });
}

// 참여자 이름을 동그라미 형식으로 표시하는 함수입니다.
function renderParticipant(participant) {
   var participantElement = document.createElement('li');
   participantElement.className = 'list-inline-item participant';
   participantElement.textContent = participant.name;
   return participantElement;
}

// 참여자 목록을 불러오는 함수입니다.
function loadParticipants() {
   sendAuthenticatedRequest("/chat/rooms/" + roomId + "/participants", "GET", null, accessToken).then(function (response) {
       var participants = response.participants;
       if (participants.length > 0) {
           participants.forEach(function (participantName) {
               var participant = {
                   name: participantName
               };
               $("#participant-list").append(renderParticipant(participant));
           });
       }
   });
}

document.getElementById('delete-room-btn').addEventListener('click', function() {
   if (confirm('정말로 방을 삭제하시겠습니까?')) {
       sendAuthenticatedRequest("/chat"+"/room/" + roomId, "DELETE", null, accessToken).then(function() {
           alert('방이 삭제되었습니다.');
           window.location.href = '/chat/rooms';
       }).catch(function(error) {
           console.error('삭제 중 오류 발생:', error);
           // 이 부분에서 사용자에게 오류 메시지를 표시하거나, 필요한 경우 다른 페이지로 리다이렉션 할 수 있습니다.
       });
   }
});


document.getElementById('leave-room-btn').addEventListener('click', function() {
   if (confirm('정말로 방을 나가시겠습니까?')) {
       leaveRoom().then(function() {
           alert('방에서 나왔습니다.');
           window.location.href = '/chat/rooms';
       });
   }
});

async function checkRoomOwner() {
   const response = await sendAuthenticatedRequest("/chat"+"/rooms/" + roomId + "/owner", "GET", null, accessToken);
   const currentUserId = await getCurrentUserId();
   if (response === currentUserId) { // 현재 사용자 ID를 확인할 수 있는 코드로 교체하세요.
       console.log('방을 만든 사용자입니다.');
       return true;
   }
   return false;
}

async function getCurrentUserId() {
   const userInfo = await sendAuthenticatedRequest("/api/v1/user/id", "GET", null, accessToken);
   return userInfo;
}
window.onload = function () {
   history.pushState({ page: 'chat_room' }, '', '');
};

window.onpopstate = function (event) {
   if (event.state && event.state.page === 'chat_room') {
       leaveRoom();
   }
};

function leaveRoom() {
   return sendAuthenticatedRequest("/chat" + "/room/" + "exit/" + roomId, "GET", null, accessToken).then(function () {
       console.log('방에서 나왔습니다.');
   });
}
function renderMessage(message) {
   var messageElement = document.createElement('li');
   messageElement.className = 'message';
   messageElement.style.display = 'flex';
   messageElement.style.flexDirection = 'row';
   messageElement.style.alignItems = 'center';
   messageElement.style.justifyContent = 'space-between';

    if(message.sender == getCurrentUserId())
    {
        messageElement.style.marginLeft ="25%";
        messageElement.style.backgroundColor="#FAFA96";
    }
    else 
        messageElement.style.marginRight = "25%";

   var messageWrapper = document.createElement('div');
   messageWrapper.className = 'message-wrapper';

   var senderContentWrapper = document.createElement('div');
   senderContentWrapper.className = 'sender-content-wrapper';

   var senderElement = document.createElement('span');
   senderElement.className = 'sender';
   senderElement.style.marginRight = '5px';
   senderElement.style.fontWeight = 'bold';
   senderElement.style.fontSize = '14px';
   senderElement.style.width = 'fit-content';
   senderElement.style.maxWidth = '70%';
   senderElement.textContent = message.sender;

   var contentElement = document.createElement('span');
   contentElement.className = 'content';
   contentElement.style.fontSize = '14px';
   contentElement.textContent = message.content;

   var timeElement = document.createElement('span');
   timeElement.className = 'timestamp';
   timeElement.style.fontSize = '12px';
   timeElement.style.color = '#999';
   timeElement.style.marginLeft = '5px';
   timeElement.style.alignSelf = 'flex-end';
   timeElement.textContent = moment(message.createdAt).format('LT');

   senderContentWrapper.appendChild(senderElement);
   senderContentWrapper.appendChild(contentElement);

   messageWrapper.appendChild(senderContentWrapper);
   messageWrapper.appendChild(timeElement);

   messageElement.appendChild(messageWrapper);

   return messageElement;
}

// 이전 코드에 없던 부분입니다.
async function sendAuthenticatedRequest(url, type, data, accessToken) {
   const response = await $.ajax({
       url: url,
       type: type,
       contentType: "application/json",
       headers: {
           'Authorization': 'Bearer ' + accessToken
       },
       data: JSON.stringify(data),
       statusCode: {
           401: async function() {
               const tokenRefreshed = await refreshAccessToken();

               if (tokenRefreshed) {
                   accessToken = localStorage.getItem('accessToken');
                   return await $.ajax({
                       url: url,
                       type: type,
                       contentType: "application/json",
                       headers: {
                           'Authorization': 'Bearer ' + accessToken
                       },
                       data: JSON.stringify(data),
                   });
               }
           }
       }
   });
   return response;
}