<#-- @ftlvariable name="contactKeys" type="String[]" -->
<#-- @ftlvariable name="socialKeys" type="String[]" -->
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
                <li><a href="/user/team/${user.id}">Команда</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a class="selected" href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <h1>Редактировать<span class="tip blue"></span></h1>

    <form action="/user/edit/${user.id}" method="post">
        <table class="contacts">
            <tr>
                <th>Имя:</th>
                <td><input name="firstName" type="text" value="${user.profile.firstName}"/></td>
            </tr>
            <tr>
                <th>Фамилия:</th>
                <td><input name="secondName" type="text" value="${user.profile.secondName}"/></td>
            </tr>
            <tr>
                <th>Пол:</th>
                <td>
                    <select name="sex">
                        <option <#if user.sex>selected="selected"</#if> value="true">женский</option>
                        <option <#if !user.sex>selected="selected"</#if> value="false">мужской</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>День рожденья (дд/мм/гггг):</th>
                <td><input name="birthday" type="text" value="${user.profile.birthDay.time?date?string.short}"/></td>
            </tr>
        </table>

        <table class="contacts">
            <#list contactKeys as key >
            <tr>
                <th>${key}:</th>
                <td><input name="contacts.${key}" type="text" value="${user.profile.contacts[key]!""}"/></td>
            </tr>
            </#list>
        </table>

        <table class="contacts">
            <#list socialKeys as key >
            <tr>
                <th>${key}:</th>
                <td><input name="social.${key}" type="text" value="${user.profile.social[key]!""}"/></td>
            </tr>
            </#list>
        </table>

        <input type="submit" value="Сохранить">
    </form>

</div>
<@foot/>
</body>
</html>
