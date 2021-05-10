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
  <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
        rel="stylesheet"/>

  <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
  <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
  <script type="text/javascript"
          src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
  <script type="text/javascript"
          src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
  <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
  <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
  <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

  <script type="text/javascript">
      $(function () {
          // 为创建按钮绑定事件，打开添加操作的模态窗口
          $("#addBtn").on("click", function () {
              // 日历插件
              $(".time").datetimepicker({
                  minView: "month",
                  language: 'zh-CN',
                  format: 'yyyy-mm-dd',
                  autoclose: true,
                  todayBtn: true,
                  pickerPosition: "bottom-left"
              });

              $.ajax({
                  url: "workbench/activity/getUserList.do",
                  type: "get",
                  data: {},
                  dataType: "json",
                  success: function (data) {
                      /*
                        需要后端返回用户列表，
                        data: [{用户1}, {用户2}, {}]
                      */
                      // 使用append，option标签将没有样式
                      // $("#create-owner").append("<option value=''></option>");
                      $.each(data, function (index, item) {
                          $("#create-owner").append("<option value='" + item.id + "'>" + item.name + "</option>");
                      });
                      /*let html = "<option></option>";
                      $.each(data, function (i, n) {
                          html += "<option value='" + n.id + "'>" + n.name + "</option>";
                      });
                      $("#create-owner").html(html);*/
                      //将当前登录的用户，设置为下拉框默认的选项
                      //在js中使用el表达式，el表达式一定要套用在字符串("")中
                      $("#create-owner").val("${sessionScope.user.id}");
                      $("#createActivityModal").modal("show");
                  }
              });

              $("#create-closeBtn").on("click", function () {
                  // 把填写的信息全部清空
                  // 清空表单替换
                  $("#activityAddForm")[0].reset();
                  $("#createActivityModal").modal("hide");
              });
          });

          //为保存按钮绑定事件，执行添加操作
          $("#saveBtn").on("click", function () {
              $.ajax({
                  url: "workbench/activity/save.do",
                  type: "post",
                  data: {
                      "owner": $.trim($("#create-owner").val()),
                      "name": $.trim($("#create-name").val()),
                      "startDate": $.trim($("#create-startDate").val()),
                      "endDate": $.trim($("#create-endDate").val()),
                      "cost": $.trim($("#create-cost").val()),
                      "description": $.trim($("#create-description").val())
                  },
                  dataType: "json",
                  success: function (data) {
                      if (data.success) {
                          // 添加成功后，刷新市场活动信息列表
                          //pageList(1,2);
                          /*
                          * $("#activityPage").bs_pagination('getOption', 'currentPage'):
                          * 		操作后停留在当前页
                          * $("#activityPage").bs_pagination('getOption', 'rowsPerPage')
                          * 		操作后维持已经设置好的每页展现的记录数
                          * 这两个参数不需要我们进行任何的修改操作
                          * 	直接使用即可
                          * */
                          //做完添加操作后，应该回到第一页，维持每页展现的记录数
                          pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                          // 清空表单，关闭模态窗口
                          // clearForm();
                          // 清空表单替换
                          $("#activityAddForm")[0].reset();
                          $("#createActivityModal").modal("hide");
                      } else {
                          alert("市场活动添加失败！")
                      }
                  }
              });
          });

          pageList(1, 2);

          //为查询按钮绑定事件，触发pageList方法
          $("#searchBtn").on("click", function () {
              /*
                点击查询按钮的时候，我们应该将搜索框中的信息保存起来,保存到隐藏域中
               */
              $("#hidden-name").val($.trim($("#search-name").val()));
              $("#hidden-owner").val($.trim($("#search-owner").val()));
              $("#hidden-startDate").val($.trim($("#search-startDate").val()));
              $("#hidden-endDate").val($.trim($("#search-endDate").val()));
              pageList(1, 2);
          });

          //为全选的复选框绑定事件，触发全选操作
          $("#qx").on("click", function () {
              $("input[name=xz]").prop("checked", this.checked);
          });

          //以下这种做法是不行的
          /*$("input[name=xz]").click(function () {
            alert(123);
          })*/
          //因为动态生成的元素，是不能够以普通绑定事件的形式来进行操作的
          /*
            动态生成的元素，我们要以on方法的形式来触发事件
            语法：
              $(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)
           */
          $("#activityBody").on("click", $("input[name=xz]"), function () {
              $("#qx").prop("checked", $("input[name=xz]").length === $("input[name=xz]:checked").length);
          });

          // 为删除按钮绑定事件，执行市场活动删除操作
          $("#deleteBtn").on("click", function () {
              // 找到所有选中的复选框
              let $xz = $("input[name=xz]:checked");
              // dom数组长度为零说明没有选中的活动记录
              if ($xz.length === 0) {
                  alert("请选择要删除的活动记录");
              } else {
                  if (confirm("确定要删除选中的活动记录吗？")) {
                      //拼接参数
                      let param = "";
                      //将$xz中的每一个dom对象遍历出来，取其value值，就相当于取得了需要删除的记录的id
                      /*for (let i = 0; i < $xz.length; i++) {
                          param += "id=" + $($xz[i]).val();
                          //如果不是最后一个元素，需要在后面追加一个&符
                          if (i < $xz.length - 1) {
                              param += "&";
                          }
                      }*/
                      $.each($xz, function (index, item) {
                          param += "id=" + item.value + "&";
                      });
                      param = param.substr(0, param.length - 1);
                      $.ajax({
                          url: "workbench/activity/delete.do",
                          type: "post",
                          data: param,
                          dataType: "json",
                          success: function (data) {
                              if (data.success) {
                                  // 刷新市场活动列表
                                  pageList(1,2);
                              } else {
                                  alert("删除市场活动失败！");
                              }
                          }
                      });
                  }
              }
          });

          // 把填写的信息全部清空
          /*function clearForm() {
              $("#create-name").val("");
              $("#create-startDate").val("");
              $("#create-endDate").val("");
              $("#create-cost").val("");
              $("#create-description").val("");
          }*/
      });

      /*
              对于所有的关系型数据库，做前端的分页相关操作的基础组件
              就是pageNo和pageSize
              pageNo:页码
              pageSize:每页展现的记录数
              pageList方法：就是发出ajax请求到后台，从后台取得最新的市场活动信息列表数据
                      通过响应回来的数据，局部刷新市场活动信息列表
              我们都在哪些情况下，需要调用pageList方法（什么情况下需要刷新一下市场活动列表）
              （1）点击左侧菜单中的"市场活动"超链接，需要刷新市场活动列表，调用pageList方法
              （2）添加，修改，删除后，需要刷新市场活动列表，调用pageList方法
              （3）点击查询按钮的时候，需要刷新市场活动列表，调用pageList方法
              （4）点击分页组件的时候，调用pageList方法
              以上为pageList方法制定了六个入口，也就是说，在以上6个操作执行完毕后，我们必须要调用pageList方法，刷新市场活动信息列表
         */
      function pageList(pageNo, pageSize) {
          $("#qx").prop("checked", false);

          //查询前，将隐藏域中保存的信息取出来，重新赋予到搜索框中
          $("#search-name").val($.trim($("#hidden-name").val()));
          $("#search-owner").val($.trim($("#hidden-owner").val()));
          $("#search-startDate").val($.trim($("#hidden-startDate").val()));
          $("#search-endDate").val($.trim($("#hidden-endDate").val()));

          $.ajax({
              url: "workbench/activity/pageList.do",
              type: "get",
              data: {
                  "pageNo": pageNo,
                  "pageSize": pageSize,
                  "name": $.trim($("#search-name").val()),
                  "owner": $.trim($("#search-owner").val()),
                  "startDate": $.trim($("#search-startDate").val()),
                  "endDate": $.trim($("#search-endDate").val())
              },
              dataType: "json",
              success: function (data) {
                  let html = "";
                  $.each(data.dataList, function (i, n) {
                      html += '<tr class="active">';
                      html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                      html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id=' + n.id + '\';">' + n.name + '</a></td>';
                      html += '<td>' + n.owner + '</td>';
                      html += '<td>' + n.startDate + '</td>';
                      html += '<td>' + n.endDate + '</td>';
                      html += '</tr>';
                  });
                  $("#activityBody").html(html);
                  //数据处理完毕后，结合分页查询，对前端展现分页信息
                  //计算总页数
                  let totalPages = data.total % pageSize === 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;
                  $("#activityPage").bs_pagination({
                      currentPage: pageNo, // 页码
                      rowsPerPage: pageSize, // 每页显示的记录条数
                      maxRowsPerPage: 20, // 每页最多显示的记录条数
                      totalPages: totalPages, // 总页数
                      totalRows: data.total, // 总记录条数

                      visiblePageLinks: 3, // 显示几个卡片

                      showGoToPage: true,
                      showRowsPerPage: true,
                      showRowsInfo: true,
                      showRowsDefaultInfo: true,

                      //该回调函数时在，点击分页组件的时候触发的
                      onChangePage: function (event, data) {
                          pageList(data.currentPage, data.rowsPerPage);
                      }
                  });
              }
          });
      }
  </script>
</head>
<body>
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
  <div class="modal-dialog" role="document" style="width: 85%;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
      </div>
      <div class="modal-body">

        <form class="form-horizontal" role="form" id="activityAddForm">

          <div class="form-group">
            <label for="create-owner" class="col-sm-2 control-label">所有者<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <select class="form-control" id="create-owner">
                <%--<option>zhangsan</option>
                <option>lisi</option>
                <option>wangwu</option>--%>
              </select>
            </div>
            <label for="create-name" class="col-sm-2 control-label">名称<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="create-name">
            </div>
          </div>

          <div class="form-group">
            <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="create-startDate" readonly placeholder="请选择日期">
            </div>
            <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="create-endDate" readonly placeholder="请选择日期">
            </div>
          </div>
          <div class="form-group">

            <label for="create-cost" class="col-sm-2 control-label">成本</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="create-cost">
            </div>
          </div>
          <div class="form-group">
            <label for="create-description" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-10" style="width: 81%;">
              <textarea class="form-control" rows="3" id="create-description"></textarea>
            </div>
          </div>

        </form>

      </div>
      <div class="modal-footer">
        <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">保存</button>--%>
        <button type="button" class="btn btn-default" id="create-closeBtn">关闭</button>
        <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
      </div>
    </div>
  </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
  <div class="modal-dialog" role="document" style="width: 85%;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">×</span>
        </button>
        <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
      </div>
      <div class="modal-body">

        <form class="form-horizontal" role="form">

          <div class="form-group">
            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <select class="form-control" id="edit-marketActivityOwner">
                <option>zhangsan</option>
                <option>lisi</option>
                <option>wangwu</option>
              </select>
            </div>
            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
            </div>
          </div>

          <div class="form-group">
            <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
            </div>
            <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
            </div>
          </div>

          <div class="form-group">
            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-cost" value="5,000">
            </div>
          </div>

          <div class="form-group">
            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-10" style="width: 81%;">
              <textarea class="form-control" rows="3" id="edit-describe">
                市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等
              </textarea>
            </div>
          </div>

        </form>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
      </div>
    </div>
  </div>
</div>


<div>
  <div style="position: relative; left: 10px; top: -10px;">
    <div class="page-header">
      <h3>市场活动列表</h3>
    </div>
  </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
  <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

    <div class="btn-toolbar" role="toolbar" style="height: 80px;">
      <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">名称</div>
            <input class="form-control" type="text" id="search-name">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">所有者</div>
            <input class="form-control" type="text" id="search-owner">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">开始日期</div>
            <input class="form-control" type="text" id="search-startDate">
          </div>
        </div>
        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">结束日期</div>
            <input class="form-control" type="text" id="search-endDate">
          </div>
        </div>

        <%--<button type="submit" class="btn btn-default">查询</button>--%>
        <button type="button" class="btn btn-default" id="searchBtn">查询</button>
      </form>
    </div>
    <div class="btn-toolbar" role="toolbar"
         style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
      <div class="btn-group" style="position: relative; top: 18%;">
        <!--
						点击创建按钮，观察两个属性和属性值
						data-toggle="modal"：
            表示触发该按钮，将要打开一个模态窗口
						data-target="#createActivityModal"：
            表示要打开哪个模态窗口，通过#id的形式找到该窗口
						现在我们是以属性和属性值的方式写在了button元素中，用来打开模态窗口
						但是这样做是有问题的：
            问题在于没有办法对按钮的功能进行扩充
						所以未来的实际项目开发，对于触发模态窗口的操作，一定不要写死在元素当中，
						应该由我们自己写js代码来操作
					-->
        <%--<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createActivityModal"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span
                class="glyphicon glyphicon-pencil"></span> 修改
        </button>--%>
        <button type="button" class="btn btn-primary" id="addBtn">
          <span class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default">
          <span class="glyphicon glyphicon-pencil"></span> 修改
        </button>
        <button type="button" class="btn btn-danger" id="deleteBtn">
          <span class="glyphicon glyphicon-minus"></span> 删除
        </button>
      </div>

    </div>
    <div style="position: relative;top: 10px;">
      <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
          <td><input type="checkbox" id="qx"/></td>
          <td>名称</td>
          <td>所有者</td>
          <td>开始日期</td>
          <td>结束日期</td>
        </tr>
        </thead>
        <tbody id="activityBody">
        <%--<tr class="active">
          <td><input type="checkbox"/></td>
          <td><a style="text-decoration: none; cursor: pointer;"
                 onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a>
          </td>
          <td>zhangsan</td>
          <td>2020-10-10</td>
          <td>2020-10-20</td>
        </tr>
        <tr class="active">
          <td><input type="checkbox"/></td>
          <td><a style="text-decoration: none; cursor: pointer;"
                 onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a>
          </td>
          <td>zhangsan</td>
          <td>2020-10-10</td>
          <td>2020-10-20</td>
        </tr>--%>
        </tbody>
      </table>
    </div>

    <div style="height: 50px; position: relative;top: 30px;">
      <div id="activityPage"></div>
    </div>
  </div>
</div>
</body>
</html>