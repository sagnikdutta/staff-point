<#-- @ftlvariable name="amount" type="java.lang.Number" -->
<#-- @ftlvariable name="groups" type="java.util.List<ru.point.view.Group<User>>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>�����, ${amount} �������</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">�������</a> &rarr; �����
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">����� �����<span class="tip red"></span></h1>


        <div id="subnav">
            <ul class="nav">
                <li><a href="#">�� ��������</a></li>
                <li><a href="#">�� ����</a></li>
                <li><a href="#">�� ��������</a></li>
            </ul>
        </div>
    </div>

    <h1>����������<span class="tip red"></span></h1>
    <span class="position">�� ������ ������� ������� ${amount} �����</span>

    <#list groups as group>
    <#if (groups?size > 1) ><h3>${group.name}</h3></#if>
    <@peopleTwoColumns users=group.elements groupby=groupby/>
    </#list>

</div>
<@foot />
</body>
</html>
