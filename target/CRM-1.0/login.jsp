<%@ page contentType="text/html; charset=utf-8" language="java" isELIgnored="false" %>
<%
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
          request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <base href="<%=basePath%>">
  <meta charset="UTF-8">
  <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
  <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
  <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
  <script>
      $(function () {
          if (window.top !== window) {
              window.top = window.location;
          }
          // 打开页面用户名输入自动获得焦点
          $("#loginAct").focus();
          /*
            $("#submitBtn").on("click", function () {
                alert("登录操作");
            });
          */
          // 将登录操作封装成函数 两种方式
          // $("#submitBtn").on("click", login);
          $("#submitBtn").on("click", function () {
              login();
          });
          // 为当前登录页窗口绑定键盘敲击事件
          // event：这个参数可以获取到所敲击的键
          $(window).keydown(function (event) {
              if (13 === event.keyCode) {
                  login();
              }
          });
      });

      // 普通自定义的function一定要放在$(function() {});的外面
      function login() {
          // alert("登录操作");
          // 获取用户名和密码，并将文本中的左右空格去掉
          let loginAct = $.trim($("#loginAct").val());
          let loginPwd = $.trim($("#loginPwd").val());
          // 非空验证
          if ("" === loginAct || "" === loginPwd) {
              // 账号密码为空，给出提示信息
              $("#msg").text("用户名和密码不能为空");
              // 非空验证不通过，需要及时强制终止该方法
              return false;
          }
          // 非空验证通过，去后台验证登录信息
          $.ajax({
              // 此处url前面不加斜杠，注意与servlet配置区别
              url: "settings/user/login.do",
              data: {
                  "loginAct": loginAct,
                  "loginPwd": loginPwd
              },
              type: "post",
              dataType: "json",
              success: function (data) {
                  /*
                    后台返回数据
                    data {
                      "success": true/false,
                      "msg": "验证结果，登录失败的原因"
                    }
                  */
                  // 如果登录成功
                  if (data.success) {
                      // 跳转到工作台初始页（欢迎页）
                      window.location.href = "workbench/index.jsp";
                  } else {
                      $("#msg").text(data.msg);
                  }
              }
          });
      }
  </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
  <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
  <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
    CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;LyanbA.</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
  <div style="position: absolute; top: 0px; right: 60px;">
    <div class="page-header">
      <h1>登录</h1>
    </div>
    <form action="workbench/index.jsp" class="form-horizontal" role="form">
      <div class="form-group form-group-lg">
        <div style="width: 350px;">
          <input class="form-control" type="text" placeholder="用户名" id="loginAct">
        </div>
        <div style="width: 350px; position: relative;top: 20px;">
          <input class="form-control" type="password" placeholder="密码" id="loginPwd">
        </div>
        <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
          <span id="msg" style="color: #ff0000"></span>
        </div>
        <%--<button type="submit" class="btn btn-primary btn-lg btn-block" style="width: 350px; position: relative;top: 45px;">登录--%>
        <%--
          注意：button写在form表单中，默认的行为是提交表单
          一定要把button的type改成button
          按钮所触发的行为应该是由我们自己手动写js代码来决定
        --%>
        <button type="button" class="btn btn-primary btn-lg btn-block" id="submitBtn"
                style="width: 350px; position: relative;top: 45px;">登录
        </button>
      </div>
    </form>
  </div>
</div>
</body>
</html>