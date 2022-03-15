package com.boa.aerd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boa.aerd.web.rest.TestUtil;

public class CarteStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteStatus.class);
        CarteStatus carteStatus1 = new CarteStatus();
        carteStatus1.setId(1L);
        CarteStatus carteStatus2 = new CarteStatus();
        carteStatus2.setId(carteStatus1.getId());
        assertThat(carteStatus1).isEqualTo(carteStatus2);
        carteStatus2.setId(2L);
        assertThat(carteStatus1).isNotEqualTo(carteStatus2);
        carteStatus1.setId(null);
        assertThat(carteStatus1).isNotEqualTo(carteStatus2);
    }
}
