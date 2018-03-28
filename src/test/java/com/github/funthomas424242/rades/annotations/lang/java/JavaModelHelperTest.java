package com.github.funthomas424242.rades.annotations.lang.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;


class JavaModelHelperTest {

    @Test
    public void testComputeEmptyPackage(){
        assertNull(JavaModelHelper.computePackageName("Hallo"));
    }


}