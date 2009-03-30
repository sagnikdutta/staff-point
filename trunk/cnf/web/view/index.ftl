<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="projects" type="java.util.List<ru.point.model.Project>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Главная</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a>
        </span>
    <@search />
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div id="info" class="box">
    <h1 class="top">Проекты<span class="tip blue"></span></h1>
    <@projectTree projects=projects />
    <h1><a href="/user">Все люди компании</a><span class="tip blue"></span></h1>
</div>
<@foot />
</body>
</html>
