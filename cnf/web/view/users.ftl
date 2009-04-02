<#-- @ftlvariable name="users" type="java.util.List<ru.point.model.User>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>�����, ${users?size} �������</@head>
<body>
<div class="header">
        <span>
            <a href="/">�������</a> &rarr; �����
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">����� �����<span class="tip red"></span></h1>
        <span class="position">�� ������ ������� ������� ${users?size} �����</span>

        <div id="subnav" class="nav">
            <ul>
                <li><a href="#">�� ��������</a></li>
                <li><a href="#">�� ����</a></li>
                <li><a href="#">�� ��������</a></li>
            </ul>
        </div>
    </div>

    <h1>����������<span class="tip red"></span></h1>

    <table id="people-list">
        <#list users as person>
        <tr>
            <td style="width: 15em; "><@userRef user=person/></td>
            <td><#list person.getActivities() as pos>${pos.getRole().getName()}<br/></#list></td>
        </tr>
        </#list>
    </table>
</div>
<@foot />
</body>
</html>
