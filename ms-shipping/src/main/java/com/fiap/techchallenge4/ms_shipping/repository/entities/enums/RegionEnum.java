package com.fiap.techchallenge4.ms_shipping.repository.entities.enums;

    public enum RegionEnum {
        NORTE("Norte"),
        NORDESTE("Nordeste"),
        SUDESTE("Sudeste"),
        SUL("Sul"),
        CENTRO_OESTE("Centro-Oeste");

        private String region;

        RegionEnum(String region) {
            this.region = region;
        }

        public String getRegion() {
            return region;
        }
    }
