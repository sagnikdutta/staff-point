<#-- @ftlvariable name="roles" type="java.util.List<ru.point.model.Role>" -->
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
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box one">
    <div id="boxheader">
        <h1 class="top">${project.name}<span class="tip red"></span></h1>
        <span class="position">В проекте трудятся ${project.activities?size} человек</span>

        <div id="subnav">
            <ul class="nav">
                <li><a href="/project/${project.id}">Люди</a><a class="arrow toggle" rel="sorting" href="#"
                                                                onclick="return false;"><img
                        src="/i/menu_arrow.png" alt=""></a>

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
                <li><a class="selected" href="/project/edit/${project.id}">Редактировать</a></li>
            </ul>
        </div>
    </div>

    <h1>Люди проекта<span class="tip red"></span></h1>

    <ul class="edit">
        <#list project.activities as activity>
        <li class="hasHidden">
            <@userRef user=activity.user/><br/>
            <span>${activity.role.name}</span>
            <a href="#" rel="/project/activity/delete/${activity.id}" class="hidden pseudo delete right"
               onclick="return false;">удалить</a>
            &nbsp;
            <a href="#" rel="/project/activity/update/${activity.id}" class="hidden pseudo delete right"
               onclick="return false;">изменить</a>
        </li>
        </#list>
    </ul>

    <h1>Добавить нового игрока<span class="tip red"></span></h1>

    <form action="/project/activity" method="post">

        <table id="newActivity">
            <tr>
                <td>Имя:</td>
                <td><input class="usersAutocomplete" type="text"/></td>
            </tr>
            <tr>
                <td>Роль:</td>
                <td>
                    <select name="role">
                        <#list roles as role>
                        <option value="${role.id}">${role.name}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Руководитель:</td>
                <td><input class="projectUsersAutocomplete" type="text"/></td>
            </tr>
        </table>

        <a id="" href="#" class="action submit">Добавить</a>

    </form>

    <h1>Родительский проект<span class="tip red"></span></h1>

    <ul class="edit">
        <li>
            <#if project.parent?? >
            <@projectRef project=project.parent />
            <#else>
            нет
            </#if>
        </li>
    </ul>
</div>
<@foot/>
</body>
</html>
