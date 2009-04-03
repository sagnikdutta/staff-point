<#-- @ftlvariable name="message" type="ru.point.view.Message" -->
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

        <div id="subnav" class="nav">
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

    <#if message?? && message.text?has_content>
    <div class="message <#if message.success>success</#if>">
        ${message.text}
        <ul>
            <#list message.list as line>
            <li>${line}</li>
            </#list>
        </ul>
    </div>
    </#if>

    <h1>Имя, контакты<span class="tip blue"></span></h1>

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
                        <option <#if user.female>selected="selected"</#if> value="true">женский</option>
                        <option <#if !user.female>selected="selected"</#if> value="false">мужской</option>
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

        <a class="action submit" href="#">Сохранить</a>
    </form>

    <h1>Фотография<span class="tip blue"></span></h1>

    <form action="/user/image/${user.id}" method="post" enctype="multipart/form-data">
        <table class="contacts">
            <tr>
                <th>Новое лицо:</th>
                <td><input name="image" type="file"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Обновить фото</a>
    </form>

    <h1>Пароль<span class="tip blue"></span></h1>

    <form action="/user/edit/password/${user.id}" method="post">

        <table class="contacts">
            <tr>
                <th>Старый пароль:</th>
                <td><input name="old" type="password" value="${user.password}"/></td>
            </tr>
            <tr>
                <th>Новый:</th>
                <td><input name="new" type="password"/></td>
            </tr>
            <tr>
                <th>Ещё разок:</th>
                <td><input name="again" type="password"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Сохранить новый пароль</a>

    </form>

</div>
<@foot/>
</body>
</html>
