<#-- @ftlvariable name="Declension" type="ru.point.utils.russian.Declension" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${user.fullName}</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a>
            <#if user.mainActivity.project??>
            &rarr; <@projectRef project=user.mainActivity.project />
            </#if>
            &rarr; <@userRef user=user />
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName}<span
                class="tip red"></span></h1>
        <span class="position">${user.mainActivity.role.name}
            <#if user.mainActivity.project??>
            в <@projectRef project=user.mainActivity.project/>
            </#if>
        </span>

        <div id="subnav">
            <ul>
                <li><a href="/user/${user.id}">Инфомация</a></li>
                <li><a href="/user/report/${user.id}">Активности</a></li>
                <li><a class="selected" href="/user/team/${user.id}">Команда</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <h1>С кем работает<span class="tip green"></span></h1>

    <#list user.activities as activity>
    <#if activity.reportTo?has_content>
    <p>
        В проекте <@projectRef project=activity.project/> работает под началом
        <a class="user"
           href="/user/team/${activity.reportTo.user.id}"><@gen sex=activity.reportTo.user.sex>${activity.reportTo.user.fullName}</@gen></a>
    </p>
    </#if>

    <#if activity.reportFrom?has_content >
    <p>
        <strong>В команде <@gen sex=user.sex>${user.profile.firstName}</@gen> работают:</strong>
    </p>

    <ul class="users squared">
        <#list activity.reportFrom as reporter>
        <li><a class="user" href="/user/team/${reporter.user.id}">${reporter.user.fullName}</a>,
            <em>${reporter.role.name}</em></li>
        </#list>
    </ul>
    </#if>
    </#list>

</div>
<@foot/>
</body>
</html>
