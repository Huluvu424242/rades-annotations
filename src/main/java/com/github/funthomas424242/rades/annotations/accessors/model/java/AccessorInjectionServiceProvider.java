package com.github.funthomas424242.rades.annotations.accessors.model.java;

/*-
 * #%L
 * rades-annotations
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;

@AutoService(AccessorInjectionService.class)
public class AccessorInjectionServiceProvider implements AccessorInjectionService {

    @Override
    public AccessorSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
        return new AccessorSrcFileCreator(filer, className, this);
    }

    @Override
    public String getNowAsISOString() {
        return JavaModelHelper.getNowAsISOString();
    }

}
