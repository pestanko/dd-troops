<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Create Role">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/roles/create"
               modelAttribute="roleCreate" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
          <div class="col-sm-10">
            <form:input path="name" cssClass="form-control"/>
            <form:errors path="name" cssClass="help-block"/>
          </div>
        </div>
          
        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
          <div class="col-sm-10">
            <form:input path="description" cssClass="form-control"/>
            <form:errors path="description" cssClass="help-block"/>
          </div>
        </div>
          
        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="attackPower" cssClass="col-sm-2 control-label">Attack Power</form:label>
          <div class="col-sm-10">
            <form:input path="attackPower" cssClass="form-control"/>
            <form:errors path="attackPower" cssClass="help-block"/>
          </div>
        </div>
          
        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="defensePower" cssClass="col-sm-2 control-label">Defense power</form:label>
          <div class="col-sm-10">
            <form:input path="defensePower" cssClass="form-control"/>
            <form:errors path="defensePower" cssClass="help-block"/>
          </div>
        </div>

      <button class="btn btn-primary" type="submit">Create role</button>
    </form:form>
  <button class="btn"
          onclick="location.href='${pageContext.request.contextPath}/${end}'">
    Return
  </button>

</jsp:attribute>
</my:pagetemplate>