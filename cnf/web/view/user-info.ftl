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

        <div id="subnav" class="nav">
            <ul>
                <li><a class="selected" href="/user/${user.id}">Инфомация</a></li>
                <li><a href="/user/report/${user.id}">Активности</a></li>
                <li><a href="/user/team/${user.id}">Команда</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <h1>Информация<span class="tip blue"></span></h1>

    <div class="left">
        <table class="contacts">
            <tr>
                <th>День рожденья:</th>
                <td>${user.profile.birthDay.time?date?string.long}<span>(до него еще ${user.profile.daysTillBirthday} дня)</span>
                </td>
            </tr>
            <tr>
                <th>E-Mail:</th>
                <td>${user.profile.contacts["e-mail"]}</td>
            </tr>
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
