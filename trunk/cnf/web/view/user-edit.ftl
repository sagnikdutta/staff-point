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
            <@projectRef project=user.mainActivity.project /> &rarr;
            <@userRef user=user />
        </span>
    <@search/>
    <@inout session=session/>
</div>
<div class="box lefty">

    <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName}
        <span class="tip red"></span></h1>
        <span class="position">${user.mainActivity.name} в
            <@projectRef project=user.mainActivity.project/></span>

    <h1>Информация<span class="tip blue"></span></h1>

    <form action="/user/edit/${user.id}" method="POST">

        <table class="contacts">
            <tr>
                <th>День рожденья:</th>
                <td><input value="${user.profile.birthDay.time?date?string.long}" type="input"/></td>
            </tr>
            <tr>
                <th>E-Mail:</th>
                <td><input value="${user.profile.contacts["e-mail"]}" type="input"/></td>
            </tr>
        </table>

        <table id="contacts" class="contacts">
            <#list user.profile.contacts?keys as key >
            <tr>
                <th>${key}:</th>
                <td><input value="${user.profile.contacts[key]}" type="input"/></td>
            </tr>
            </#list>
        </table>

        <input type="submit"/>

    </form>

    <img src="/i/user-128.png"/>

</div>
<@foot/>
</body>
</html>
