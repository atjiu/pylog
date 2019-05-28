<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>PYLOG</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="/css/app.css">

  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<br>
<br>
<div class="container">
  <h1>
  ${name!"PYLOG"}
  </h1>
  <div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
      <#if names??>
        <#list names as n>
          <li role="presentation" <#if n_index == 0>class="active"</#if>>
            <a href="#${n!}" data-id="${n!}" role="tab" data-toggle="tab">${n!}</a>
          </li>
        </#list>
      </#if>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
     <#if names??>
       <#list names as n>
        <div role="tabpanel" class="tab-pane <#if n_index == 0>active</#if>" id="${n!}"></div>
       </#list>
     </#if>
    </div>

  </div>
</div>

<script>

  // 随机生成字符串
  /*function makeId(length) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

  var id = makeId(6);*/

  if (window.WebSocket) {
    // Create WebSocket connection.
    const socket = new WebSocket('ws://${host}:${port}/websocket');

    // 封装 emit 方法
    function emit(type, obj) {
      socket.send(JSON.stringify({type: type, payload: obj}));
    }

    // Connection opened
    socket.addEventListener('open', function (event) {
      // emit('text', 'Hello Server!');
    });

    // Connection closed
    socket.addEventListener('close', function (event) {
      // emit('text', 'i am leave!');
    });

    // Listen for messages
    socket.addEventListener('message', function (event) {
      var message = JSON.parse(event.data);
      if (message.type === 'fetchLogs') {
        $.each(message.payload, function (name) {
          emit(name, {});
        })
      } else {
        $("#" + message.type).append("<div>" + message.payload + "</div>");
        var line = $("#" + message.type).find('div').length;
        if (line > 200) {
          $("#" + message.type).find("div:eq(0)").remove();
        }
        keepScrollBottom(message.type);
      }
    });

    function keepScrollBottom(type) {
      var objDiv = document.getElementById(type);
      objDiv.scrollTop = objDiv.scrollHeight;
    }

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
      keepScrollBottom(e.target.getAttribute("data-id"));
    });

    setTimeout(function () {
      emit('fetchLogs', {});
    }, 700);
  }
</script>
</body>
</html>
