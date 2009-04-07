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
            <a href="/">Главная</a> &rarr;
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
        <span class="position">В проекте трудятся ${project.activities?size} человек</span>

        <div id="subnav" class="nav">
            <ul>
                <li><a href="/project/${project.id}">Люди</a>
                    <a class="arrow toggle" rel="sorting" href="#" onclick="return false;"><img src="/i/menu_arrow.gif" alt=""/></a>

                    <div id="sorting" class="menu hidden">
                        <ul>
                            <li><a href="/project/${project.id}/by/name">По алфавиту</a></li>
                            <li><a href="/project/${project.id}/by/role">По роли</a></li>
                            <li><a href="/project/${project.id}/by/birthday">По дате рождения</a></li>
                        </ul>
                    </div>
                </li>
                <li><a class="selected" href="/project/report/${project.id}">Активности</a></li>
                <li><a href="/edit">Редактировать</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">Люди ау!<span class="tip red"></span></h1>

    <ul>
        <#list project.activities as position>
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
