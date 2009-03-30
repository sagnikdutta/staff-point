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
                <li><a href="/report/activity/">����������</a></li>
                <li><a href="/team">�������</a></li>
                <li><a href="/edit">�������������</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">���� �������<span class="tip red"></span></h1>
    <table id="people-list">
        <#list project.activities?sort_by("id") as position>
        <tr>
            <td style="width: 15em; "><@userRef user=position.user/></td>
            <td>
            ${position.role.name}
            <td>
        </tr>
        </#list>
    </table>
</div>
<@foot/>
</body>
</html>
