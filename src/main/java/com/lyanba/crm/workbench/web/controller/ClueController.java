package com.lyanba.crm.workbench.web.controller;

import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.settings.service.impl.UserServiceImpl;
import com.lyanba.crm.utils.DateTimeUtil;
import com.lyanba.crm.utils.PrintJson;
import com.lyanba.crm.utils.ServiceFactory;
import com.lyanba.crm.utils.UUIDUtil;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.Clue;
import com.lyanba.crm.workbench.domain.Tran;
import com.lyanba.crm.workbench.service.ActivityService;
import com.lyanba.crm.workbench.service.ClueService;
import com.lyanba.crm.workbench.service.impl.ActivityServiceImpl;
import com.lyanba.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ClueController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 08:51
 */
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(">---------- 进入到线索控制器 ----------<");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/clue/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/clue/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/clue/getActivityListByClueId.do".equals(path)) {
            getActivityListByClueId(request, response);
        } else if ("/workbench/clue/unbind.do".equals(path)) {
            unbind(request, response);
        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {
            getActivityListByNameAndNotByClueId(request, response);
        } else if ("/workbench/clue/bind.do".equals(path)) {
            bind(request, response);
        } else if ("/workbench/clue/getActivityListByName.do".equals(path)) {
            getActivityListByName(request, response);
        } else if ("/workbench/clue/convert.do".equals(path)) {
            convert(request, response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(">---------- 执行线索转换的操作 ----------<");
        String clueId = request.getParameter("clueId");
        String flag = request.getParameter("flag");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Tran tran = null;
        if ("a".equals(flag)) {
            tran = new Tran();
            //接收交易表单中的参数
            String money = request.getParameter("money"); // 交易金额
            String name = request.getParameter("name"); // 交易名称
            String expectedDate = request.getParameter("expectedDate"); // 预计成交日期
            String stage = request.getParameter("stage"); // 阶段
            String activityId = request.getParameter("activityId"); // 选中的市场活动ID
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            tran.setId(id);
            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
        }
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean result = clueService.convert(clueId, tran, createBy);
        if (result) {
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
        }
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 查询市场活动列表（根据名称模糊查） ----------<");
        String activityName = request.getParameter("activityName");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.getActivityListByName(activityName);
        PrintJson.printJsonObj(response, activityList);
    }

    private void bind(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 执行关联市场活动的操作 ----------<");
        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.bind(cid, aids);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 查询市场活动列表（根据名称模糊查 + 排除掉已经关联指定线索的列表） ----------<");
        String activityName = request.getParameter("activityName");
        String clueId = request.getParameter("clueId");
        Map<String, String> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response, activityList);
    }

    private void unbind(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 执行解除关联操作 ----------<");
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.unbind(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 根据线索ID查询市场活动列表 ----------<");
        String clueId = request.getParameter("clueId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.getActivityListByClueId(clueId);
        PrintJson.printJsonObj(response, activityList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(">---------- 进入到线索详细信息页 ----------<");
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = clueService.detail(id);
        request.setAttribute("clue", clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 进入到添加线索功能 ----------<");
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = clueService.save(clue);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 取得用户信息列表 ----------<");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
