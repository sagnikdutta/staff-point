<#-- @ftlvariable name="amount" type="java.lang.Number" -->
<#-- @ftlvariable name="groups" type="java.util.List<ru.point.view.Group<User>>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Поиск, ${amount} найдено</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">Главная</a> &rarr; Поиск
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">Поиск Людей<span class="tip red"></span></h1>


        <div id="subnav">
            <ul class="nav">
                <li><a href="#">По алфавиту</a></li>
                <li><a href="#">По роли</a></li>
                <li><a href="#">По проектам</a></li>
            </ul>
        </div>
    </div>

    <h1>Результаты<span class="tip red"></span></h1>
    <span class="position">По вашему запросу найдено ${amount} людей</span>

    <#list groups as group>
    <#if (groups?size > 1) ><h3>${group.name}</h3></#if>
    <@peopleTwoColumns users=group.elements groupby=groupby/>
    </#list>

</div>
<@foot />
</body>
</html>
