<#-- @ftlvariable name="users" type="java.util.List<ru.point.model.User>" -->
<#list users as user>
${user.fullName} / ${user.getLogin()} | ${user.id} 
</#list>