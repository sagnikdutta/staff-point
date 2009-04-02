<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="project" type="ru.point.model.Project" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${project.name}</@head>
<body>
<div class="header">
        <span>
            <a href="/">�������</a> &rarr;
            <@projectRef project=project />
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box one">
    <div id="boxheader">
        <h1 class="top">${project.name}<span class="tip red"></span></h1>
        <span class="position">� ������� �������� ${project.activities?size} �������</span>

        <div id="subnav">
            <ul>
                <li><a href="/project/${project.id}">����������</a></li>
                <li><a class="selected" href="/project/report/${project.id}">����������</a></li>
                <li><a href="/project/edit/${project.id}">�������������</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">���� ��!<span class="tip red"></span></h1>

    <ul>
        <#list project.activities?sort_by("id") as position>
        <li class="position"><@userRef user=position.user/> &mdash; ${position.role.name}<br/>
            <ul>
                <#list position.reports as report>
                <li>${report.start.time?date} ~ ${report.end.time?date}:<br/>
                    ${report.text?html?replace("\n", "<br/>")} </li>
                </#list>
            </ul>
        </li>
        </#list>
    </ul>

</div>
<@foot/>
</body>
</html>
