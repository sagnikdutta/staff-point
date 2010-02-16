<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="ru.point.view.Message" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Регистрация</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">Главная</a>
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">Добро пожаловать<span class="tip red"></span></h1>
        <span class="position">Будем знакомы!</span>
    </div>


    <h1>Войти<span class="tip blue"></span></h1>
    <span class="position">Уже с нами?</span>

    <form id="loginForm" action="/login" method="post">
        <table class="contacts">
            <tr>
                <th>E-Mail (логин):</th>
                <td><input name="login" type="text"/></td>
            </tr>
            <tr>
                <th>Пароль:</th>
                <td><input name="password" type="password"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Войти</a>
    </form>

    <h1><a class="toggle" rel="newUserForm">Зарегистрироваться</a><span class="tip blue"></span></h1>
    <span class="position">Логин и 2 раза пароль, всё просто</span>

    <form id="newUserForm" class="hidden" action="/user" method="post">
        <table class="contacts">
            <tr>
                <th>E-Mail (логин):</th>
                <td><input name="email" type="text" value="${email!""}"/></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <th>Пароль:</th>
                <td><input name="password" type="password"/></td>
            </tr>
            <tr>
                <th>Ещё раз пароль:</th>
                <td><input name="again" type="password"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Зарегистрироваться</a>
    </form>

    <h1><a class="toggle" rel="inviteForm ">Пригласить</a><span class="tip blue"></span></h1>
    <span class="position">Новый сотрудник? Пригласите его</span>

    <form id="inviteForm" class="hidden" action="/user" method="post">
        <table class="contacts">
            <tr>
                <th>E-Mail (логин):</th>
                <td><input name="email" type="text" value="${email!""}"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Пригласить</a>
    </form>

    <h1><a class="toggle" rel="adminForm">Администирование</a><span class="tip blue"></span></h1>
    <span class="position">Добавить или удалить новый проект? Сменился руководитель?</span>

    <form id="adminForm" class="hidden" action="/user" method="post">
        <table class="contacts">
            <tr>
                <th>Я знаю пароль:</th>
                <td><input name="email" type="password"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">Я осознаю что я делаю!</a>
    </form>

</div>
<@foot/>
</body>
</html>
