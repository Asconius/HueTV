package com.asconius.huetv;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class MainActivityTest {

    @Test
    public void convertImage() {
        assertThat(new MainActivity().convert(null)).isNull();
    }
}
