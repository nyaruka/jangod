/**********************************************************************
Copyright (c) 2009 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.template.tag;

import java.io.IOException;
import java.util.List;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.compile.Node;
import net.asfun.template.compile.Tag;
import net.asfun.template.parse.JangodParser;
import net.asfun.template.parse.ParserException;
import net.asfun.template.util.HelperStringTokenizer;
import net.asfun.template.util.TemplateLoader;

/**
 * {% include 'sidebar.html' %}
 * {% include var_fileName %}
 * @author anysome
 *
 */
public class IncludeTag implements Tag{
	
	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler)
			throws CompilerException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new CompilerException("include tag expects 1 helper >>> " + helper.length);
		}
		String templateFile = compiler.resolveString(helper[0]);
		try {
			TemplateLoader loader = new TemplateLoader();
			loader.setBase(compiler.getConfig().getTemplateRoot());
			JangodParser parser = new JangodParser(loader.getReader(templateFile));
			JangodCompiler child = compiler.copy();
			child.assignRuntimeScope(JangodCompiler.INSERT_FLAG, true, 1);
			return child.render(parser);
		} catch (IOException e) {
			throw new CompilerException(e.getMessage());
		} catch (ParserException e) {
			throw new CompilerException(e.getMessage());
		}
	}

	@Override
	public String getEndTagName() {
		return null;
	}

	@Override
	public String getName() {
		return "include";
	}

}
