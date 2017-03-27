<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="col-sm-3 col-md-2 sidebar">
	<div class="tree">
		<ul style="padding-left: 0px;" class="list-group">
			<#list permissions as menu>
				<#if menu.children?size == 0>
					<li class="list-group-item tree-closed"><a href="${APP_PATH }${menu.url}"><i class="fa fa-fw ${menu.icon }"></i> ${menu.name}</a></li>
				<#else>
					<li class="list-group-item tree-closed"><span><i class="fa fa-fw ${menu.icon }"></i> ${menu.name} <span class="badge" style="float: right">${menu.children?size}</span></span>
						<ul style="margin-top: 10px; display: none;">
							<#list menu.children as childMenu>
								<li style="height: 30px;">
									<a href="${APP_PATH }${childMenu.url}"><i class="fa fa-fw ${childMenu.icon }"></i> ${childMenu.name}</a>
								</li>
							</#list>
						</ul>
					</li>
				</#if>
			</#list>
		</ul>
	</div>
</div>