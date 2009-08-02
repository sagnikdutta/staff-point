<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${user.fullName}</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">Главная</a>
            <#if user.mainActivity?? && user.mainActivity.project??>
            &rarr; <@projectRef project=user.mainActivity.project />
            </#if>
            &rarr; <@userRef user=user />
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName}<span
                class="tip red"></span></h1>
        <#if user.mainActivity?? && user.mainActivity.project??>
            <span class="position">${user.mainActivity.role.name}
            в <@projectRef project=user.mainActivity.project/></span>
        <#else>
        <span class="position">Нет активностей</span>
        </#if>


        <div id="subnav">
            <ul class="nav">
                <li><a class="selected" href="/user/${user.id}">Инфомация</a></li>
                <#if (user.activities?? && user.activities?size > 0) >
                <li><a href="/user/report/${user.id}">Активности</a></li>
                <li><a href="/user/team/${user.id}">Команда</a></li>
                </#if>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <h1>Информация<span class="tip blue"></span></h1>

    <div class="left">
        <table class="contacts">
            <#if user.profile.birthDay??>
            <tr>
                <th>День рожденья:</th>
                <td>${user.profile.birthDay.time?date?string.long}<span>(до него еще ${user.profile.daysTillBirthday} дня)</span>
                </td>
            </tr>
            </#if>
        </table>

        <table class="contacts">
            <#list user.profile.contacts?keys as key >
            <tr>
                <th>${key}:</th>
                <td>${user.profile.contacts[key]}</td>
            </tr>
            </#list>
        </table>

        <table class="contacts">
            <#list user.profile.social?keys as key >
            <tr>
                <th>${key}:</th>
                <td>${user.profile.social[key]}</td>
            </tr>
            </#list>
        </table>
    </div>

    <div class="right"><a href="/user/image/o/${user.id}"><img src="/user/image/m/${user.id}"/></a></div>
</div>
<@foot/>
</body>
</html>
