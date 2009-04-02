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

        <div id="subnav">
            <ul>
                <li><a class="selected" href="/project/${project.id}">Информация</a></li>
                <li><a href="/project/report/${project.id}">Активности</a></li>
                <li><a href="/edit">Редактировать</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">Люди проекта<span class="tip red"></span></h1>

    <#assign middle = project.activities?size/2>
    <ul class="people left">
        <#list 1..middle as idx>
        <li>
            <span><a href="/user/${project.activities[idx].user.id}"><img src="/user/image/s/${project.activities[idx].user.id}"/></a></span>
            <h4><@userRef user=project.activities[idx].user/></h4>

            <p class="picture-note">${project.activities[idx].role.name}</p>
        </li>
        </#list>
    </ul>
    <ul class="people left">
        <#list middle+1..project.activities?size-1 as idx>
        <li>
            <span><a href="/user/${project.activities[idx].user.id}"><img src="/user/image/s/${project.activities[idx].user.id}"/></a></span>
            <h4><@userRef user=project.activities[idx].user/></h4>

            <p class="picture-note">${project.activities[idx].role.name}</p>
        </li>
        </#list>
    </ul>
</div>
<@foot/>
</body>
</html>
