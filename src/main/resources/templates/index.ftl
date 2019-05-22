<#include "layout/layout.ftl"/>
<@html page_title="任务列表" page_tab="task">
    <div class="daohang">
        <b>任务列表</b>
        <a href="/create" class="btn btn-primary btn-sm">创建任务</a>
    </div>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>任务名</th>
            <th>创建时间</th>
            <th>最后部署时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list tasks as task>
        <tr>
            <td>${task.id?c}</td>
            <td>${task.name!}</td>
            <td>${task.inTime!}</td>
            <td>${task.lastDeployTime!}</td>
            <td></td>
        </tr>
        </#list>
        </tbody>
    </table>
</@html>
