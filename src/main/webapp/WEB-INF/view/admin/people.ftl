<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="ru.point.view.Message" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#include "../common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Администрация</@head>
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
        <span class="position">!</span>

        <div id="subnav">
            <ul class="nav">
                <li><a href="/admin/projects">Проекты</a></li>
                <li><a href="/admin/managers">Руководители</a></li>
                <li><a href="/admin/people">Люди</a></li>
                <li><a href="/admin/misc">Разное</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">Удаление людского состава<span class="tip red"></span></h1>
    <span class="position">!</span>

</div>
<@foot/>
</body>
</html>
