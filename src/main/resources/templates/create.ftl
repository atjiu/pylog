<#include "layout/layout.ftl"/>
<@html page_title="任务列表" page_tab="task">
    <div class="daohang">
        <b>创建任务</b>
    </div>
    <form action="/create" method="post" class="form-horizontal" role="form">

        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">任务名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" placeholder="任务名">
            </div>
        </div>

        <div class="form-group">
            <label for="command" class="col-sm-2 control-label">命令</label>
            <div class="col-sm-10">
                <textarea name="command" id="command" class="form-control" rows="5" placeholder="命令"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <input type="checkbox" name="stdout" id="stdout"/>
                <label for="stdout">是否有输出</label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <button type="submit" class="btn btn-primary">提交</button>
            </div>
        </div>
    </form>
</@html>
