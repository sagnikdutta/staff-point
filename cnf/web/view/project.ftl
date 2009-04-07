<#-- @ftlvariable name="groupby" type="String" -->
<#-- @ftlvariable name="groups" type="java.util.List<ru.point.view.Group<ru.point.model.Activity>>" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="project" type="ru.point.model.Project" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${project.name}</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a> &rarr;
            <@projectRef project=project />
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box one">
    <div id="boxheader">
        <h1 class="top">${project.name}<span class="tip red"></span></h1>
        <span class="position">В проекте трудятся ${project.activities?size} человек</span>

        <div id="subnav" class="nav">
            <ul>
                <li><a class="selected" href="/project/${project.id}">Люди</a><a class="arrow toggle" rel="sorting" href="#" onclick="return false;"><img src="/i/menu_arrow.png" alt=""></a>
                    <div id="sorting" class="menu hidden">
                        <h3>Сортировать по:</h3>
                        <ul>
                            <li><a href="/project/${project.id}/by/name">алфавиту</a></li>
                            <li><a href="/project/${project.id}/by/role">роли</a></li>
                            <li><a href="/project/${project.id}/by/birthday">дате рождения</a></li>
                        </ul>
                    </div>
                </li>
                <li><a href="/project/report/${project.id}">Активности</a></li>
                <li><a href="/edit">Редактировать</a></li>
            </ul>
        </div>
    </div>

    <h1 class="top">Люди проекта<span class="tip red"></span></h1>

    <#list groups as group>
    <#if (groups?size > 1) ><h3>${group.name}</h3></#if>
    <@peopleTwoColumns activities=group.elements groupby=groupby/>
    </#list>

</div>
<@foot/>
</body>
</html>
