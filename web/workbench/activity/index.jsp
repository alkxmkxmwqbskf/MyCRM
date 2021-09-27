<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
//分页插件
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	$(function(){
		$("#createBtn").click(function(){
			//时间控件
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN', //语言包
				format: 'yyyy-mm-dd', //格式化
				autoclose: true, //关闭按钮
				todayBtn: true,  //显示今天
				pickerPosition: "bottom-left"
			});

			//在打开模态窗口前, 走后台获取用户信息;
			$.ajax({
				url: "workbench/activity/getUserList.do",
				type: "get",
				dataType: "json",
				success: function(data){
					/*
                        data:
                          [{"id": ?, "lockState": ?}, {""}]
                     */
					var html = "";
					$.each(data, function(i, n){
						html += "<option value='"+n.id+"'>"+n.name+"</option>";
					})

					$("#create-owner").html(html);
					//在js中使用el表达式一定要套在字符串中;
					var id = "${sessionScope.user.id}";
					$("#create-owner").val(id);

					//所有者下拉框处理完毕, 打开模态
					$("#createActivityModal").modal("show");
				}
			})
		})

		//为保存按钮添加事件, 保存用户提交的数据
		$(function () {
			$("#saveBtn").click(function () {
				$.ajax({
					url: "workbench/activity/save.do",
					data: {
						"owner": $.trim($("#create-owner").val()),
						"name":  $.trim($("#create-name").val()),
						"startDate": $.trim($("#create-startDate").val()),
						"endDate": $.trim($("#create-endDate").val()),
						"cost": $.trim($("#create-cost").val()),
						"description": $.trim($("#create-description").val())
					},
					type: "post",
					dataType: "json",
					success: function(data){
						/*
                            data {"success": true}
                         */
						if (data.success){
							//添加成功
							//刷新市场活动信息列表

							//关闭添加操作的模态窗口后, 数显数据
							$("#createActivityModal").modal("hide");
						}else {
							alert("市场活动数据添加失败!");
						}
					}
				})
			})
		})

		//页面加载完毕后触发一个方法
		pageList(1, 2);
		
		//为按钮绑定事件
		$("#queryBtn").click(function () {
			/*
				点击分页按钮的时候
			 */
			$("#hidden-name").val($.trim($("#query-name").val()));
			$("#hidden-owner").val($.trim($("#query-owner").val()));
			$("#hidden-startDate").val($.trim($("#query-startDate").val()));
			$("#hidden-endDate").val($.trim($("#query-endDate").val()));

			pageList(1, 2);
		})

		//为全选的复选框绑定事件, 触发全选
		$("#activity-selectAll").click(function () {
			//通过标签选择器找到名称为activity-selectSingle的标签, 把当前标签activity-selectSingle的状态赋值给activity-selectAll;
			$("input[name=activity-selectSingle]").prop("checked", this.checked);
		})

		$("#activityBody").on("click", $("input[name=activity-selectSingle]"), function(){
			$("#activity-selectAll").prop("checked", $("input[name=activity-selectSingle]").length === $("input[name=activity-selectSingle]:checked").length);
		})
	});

	/*
		分页查询: pageNo & pageSize
	 */

	function  pageList(pageNo, pageSize) {
		//查询前, 将隐藏域标签中的数据取出来, 重新赋值到搜索框;

		$("#query-name").val($.trim($("#hidden-name").val()));
		$("#query-owner").val($.trim($("#hidden-owner").val()));
		$("#query-startDate").val($.trim($("#hidden-startDate").val()));
		$("#query-endDate").val($.trim($("#hidden-endDate").val()));

		$.ajax({
			url: "workbench/activity/getPageList.do",
			type: "get",
			data: {
				"pageNo": pageNo,
				"pageSize": pageSize,
				"name": $.trim($("#query-name").val()),
				"owner": $.trim($("#query-owner").val()),
				"startDate": $.trim($("#query-startDate").val()),
				"endDate": $.trim($("#query-endDate").val())
			},
			dataType: "json",
			success: function (data) {
				/*
					data: 市场活动列表 {"total": 100}, "dataList":[{"活动1":?}, {"活动2": ?}]
				 */
				var html = "";
				$.each(data.dataList, function(i, n){
					html += '<tr class="active">';
					html += '<td><input type="checkbox" value="'+n.id+'" name="activity-selectSingle"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\';">'+n.name+'</a></td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.startDate+'</td>';
					html += '<td>'+n.endDate+'</td>';
					html += '</tr>';
				})

				$("#activityBody").html(html);

				//计算总页数
				var totalPages = (data.total % pageSize === 0) ? data.total/pageSize : Math.ceil(data.total/pageSize);
				//数据处理完毕后, 结合前端框架做分页
				$("#activityPage").bs_pagination({
					currentPage: pageNo, //页码
					rowsPerPage: pageSize, //每页显示的记录条数
					maxRowsPerPage: 20, //每页最多显示的记录数
					totalPages: totalPages, //总页数
					totalRows: data.total, //总记录数

					visiblePageLinks: 5, //显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,
					//该回调函数在点击分页组件时触发的
					onChangePage: function(event, data){
						pageList(data.currentPage, data.rowsPerPage);
					}
				});
			}
		})
	}
	
</script>
</head>
<body>
//隐藏域标签
<input type="hidden" id="hidden-name">
<input type="hidden" id="hidden-owner">
<input type="hidden" id="hidden-startDate">
<input type="hidden" id="hidden-endDate">
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
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
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
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
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
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button type="button" id="queryBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
			<!-- 市场活动创建按钮
				data-toggle-modal: 表示触发该按钮, 触发模态窗口;
				问题: 没有办法对按钮功能进行扩充;
			-->
				  <button type="button" class="btn btn-primary" data-toggle="modal" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="activity-selectAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			<!-- 此处引入分页组件 -->
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>