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
				Reportar incidencia o comentario.
			</p>
		</div>
	</div>
</div>
<div class="hr">
</div>
<!-- SUBHEADER -->

<!-- CONTENT -->
<!-- CONTACT FORM -->
<form id="frmEmail" name="frmEmail" action="" method="post">
<div class="row">
	<div class="twelve columns">
		<div class="wrapcontact">
			<div class="form">
				<div class="five columns">
					<label>Correo electrónico</label>
					<input type="email" id="email" name="email" class="smoothborder" placeholder="su correo [direccion@dominio.com] *" pattern="^[\w._%-]+@[\w.-]+\.[a-zA-Z]{2,4}$" maxlength="80"/>
				</div>
				<div class="twelve columns">
					<label>Mensaje</label>
					<textarea id="comment" name="comment" class="smoothborder ctextarea" style="resize:none" rows="10" maxlength="600" placeholder="mensaje, comentario *"></textarea>
					<input id="enviar" type="button" class="success button" value="Enviar" onclick="enviarMail()" />
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<!-- CONTACT FORM -->
<br>
<br>
<!-- CONTENT -->

<%@include file="comun/footer.jsp"%>