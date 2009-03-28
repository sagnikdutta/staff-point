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

    <@search />
    <@inout session=session/>
</div>
<div class="box one">
    <h1 class="top">Все люди проекта:</h1>
    <table id="people-list">
        <#list project.activities?sort_by("id") as activity>
        <tr>
            <th><@userRef user=activity.user/></th>
            <td>
            ${activity.name}
            <td>
        </tr>
        </#list>
    </table>
</div>
<@foot/>
</body>
</html>
