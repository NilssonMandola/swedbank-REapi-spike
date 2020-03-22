package com.crankoid.reverseengineeringapi.service.internal.seb.client.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class SebAccount {
    private Integer rowId;
    private Long kundNrPersorg;
    private String kontoNr;
    private String ktoslagTxt;
    private Double bokfSaldo;
    private Double dispBel;
    private String ktobenTxt;
    private Double kredbel;
    private String khav;
    private String ktoslagKod;
    private String ktoutdrUtskr;

    @JsonGetter("ROW_ID")
    public Integer getRowId() {
        return rowId;
    }

    @JsonGetter("KUND_NR_PERSORG")
    public Long getKundNrPersorg() {
        return kundNrPersorg;
    }

    @JsonGetter("KONTO_NR")
    public String getKontoNr() {
        return kontoNr;
    }

    @JsonGetter("KTOSLAG_TXT")
    public String getKtoslagTxt() {
        return ktoslagTxt;
    }

    @JsonGetter("BOKF_SALDO")
    public Double getBokfSaldo() {
        return bokfSaldo;
    }

    @JsonGetter("DISP_BEL")
    public Double getDispBel() {
        return dispBel;
    }

    @JsonGetter("KTOBEN_TXT")
    public String getKtobenTxt() {
        return ktobenTxt;
    }

    @JsonGetter("KREDBEL")
    public Double getKredbel() {
        return kredbel;
    }

    @JsonGetter("KHAV")
    public String getKhav() {
        return khav;
    }

    @JsonGetter("KTOSLAG_KOD")
    public String getKtoslagKod() {
        return ktoslagKod;
    }

    @JsonGetter("KTOUTDR_UTSKR")
    public String getKtoutdrUtskr() {
        return ktoutdrUtskr;
    }
}
