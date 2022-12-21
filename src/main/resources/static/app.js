var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  }
  else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}

function connect() {
  const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
    // connectHeaders: {
    //   login: 'user',
    //   passcode: 'password',
    // },
    // debug: function (str) {
    //   console.log(str);
    // },
    // reconnectDelay: 5000,
    // heartbeatIncoming: 4000,
    // heartbeatOutgoing: 4000,
  });

  stompClient.onConnect = (frame) => {
    // Do something, all subscribes must be done is this callback
    // This is needed because this will be executed after a (re)connect
    console.log('STOMP CONNECTED', frame);
  };

  stompClient.onStompError = function (frame) {
    // Will be invoked in case of error encountered at Broker
    // Bad login/passcode typically will cause an error
    // Complaint brokers will set message header with a brief message. Body may contain details.
    // Compliant brokers will terminate the connection after any error
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
  };
  stompClient.activate();

  // var socket = new SockJS('/gs-guide-websocket');
  // var url = "ws://127.0.0.1:8080/ws";
  // stompClient = Stomp.over(socket);
  // stompClient.connect({}, function (frame) {
  //   setConnected(true);
  //   console.log('Connected: ' + frame);
  //   stompClient.subscribe('/topic/greetings', function (greeting) {
  //     showGreeting(JSON.parse(greeting.body).content);
  //   });
  // });
}


function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $( "#connect" ).click(function() { connect(); });
  $( "#disconnect" ).click(function() { disconnect(); });
  $( "#send" ).click(function() { sendName(); });
});