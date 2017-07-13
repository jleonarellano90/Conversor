<%@include file="comun/header.jsp"%>
<script src="${pageContext.servletContext.contextPath}/js/sistema/inicio.js"></script>

<!-- HEADER -->
<div class="row">
	<div class="four columns">
		<div class="logo">
			<a href="inicio.do">
				<img src="${pageContext.servletContext.contextPath}/images/pdfxls1.png" width="60%">
			</a>
		</div>
	</div>
	<div class="eight columns noleftmarg">		
			<nav id="nav-wrap">
				<ul class="nav-bar sf-menu">
					<li class="current">
						<a href="inicio.do">Inicio</a>
					</li>
					<li>
						<a href="contacto.do">Contacto</a>
					</li>				
				</ul>
			</nav>
		</div>
</div>
<div class="clear">
</div>
<!-- HEADER -->

<!-- SUBHEADER -->
<div id="subheader">
	<div class="row">
		<div class="twelve columns" align="center">
			<p class="left">
				${titulo}
			</p>
			<p class="right" style="font-size:14px;">
			</p>
		</div>
	</div>
</div>
<div class="hr">
</div>
<!-- SUBHEADER -->

<!-- CONTENT -->
<div class="row">
	<div class="twelve columns">
		<div class="wrapcontact">
			<h5>${operacion}</h5>
			<div align="center">
				<p>${resultado}</p>
			</div>
			<br>
			<div align="center">
				<a class="success button" href="contacto.do">Regresar</a>
				<a class="success button" href="inicio.do">Inicio</a>
			</div>	
		</div>
	</div>									
</div>
<br>
<br>
<!-- CONTENT -->

<%@include file="comun/footer.jsp"%>