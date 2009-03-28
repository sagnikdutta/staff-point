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
            <a href="/">Главная</a> &rarr;
            <@projectRef project=user.mainPosition.project /> &rarr;
            <@userRef user=user />
        </span>
    <@search/>
    <@inout session=session/>
</div>
<div id="info" class="box">
    <div class="info">
        <img alt="" src="/i/user-128.png">
    </div>
    <div class="content">
        <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName} </h1>
        <span class="position">${user.mainPosition.name} в
            <@projectRef project=user.mainPosition.project/> &rarr;
            <a class="activity" href="/report/position/${user.mainPosition.id}">Активности</a></span>

        <h1>Информация</h1>
        <table class="contacts">
            <tr>
                <th>День рожденья:</th>
                <td>${user.profile.birthDay.time?date?string.long}<span> (до него еще 292 дня)</span></td>
            </tr>
            <tr>
                <th>E-Mail:</th>
                <td>${user.profile.contacts["e-mail"]}</td>
            </tr>
        </table>

        <table id="contacts" class="contacts hidden">
            <#list user.profile.contacts?keys as key >
            <tr>
                <th>${key}:</th>
                <td>${user.profile.contacts[key]}</td>
            </tr>
            </#list>
        </table>

        <a class="toggle more" rel="contacts" href="#">Все контакты&nbsp;&darr;</a>

        <h1>С кем работает</h1>

        <#list user.positions as position>
        <#if position.reportTo?has_content>
        <p>
            В проекте <@projectRef project=position.project/> работает под началом
            <@userRef user=position.reportTo.user/>
        </p>
        </#if>

        <#if position.reportFrom?has_content >
        <p>
            <strong>В команде ${user.profile.firstName} работают:</strong>
        </p>

        <ul class="">
            <#list position.reportFrom as reporter>
            <li><@userRef user=reporter.user/>, <em>${reporter.name}</em></li>
            </#list>
        </ul>
        </#if>
        </#list>

    </div>
</div>
<@foot/>
</body>
</html>
