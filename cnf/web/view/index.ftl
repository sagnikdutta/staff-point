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
    <@inout session=session/>
</div>
<div id="info" class="box">
    <div class="info">
        <h1 class="top">Проекты</h1>
        <ul>
            <#list projects?sort_by("id") as project>
            <li><@projectRef project=project /></li>
            </#list>
        </ul>
    </div>
    <div class="content">
        <h1 class="top">Что на <a href="/board">форуме</a></h1>
    </div>

    <div class="clear">&nbsp;</div>
</div>
<@foot />
</body>
</html>
