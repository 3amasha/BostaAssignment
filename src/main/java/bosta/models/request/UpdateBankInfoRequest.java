package bosta.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateBankInfoRequest {

    //TODO: Get correct OTP

    public BankInfo bankInfo;
    public String paymentInfoOtp;

    public UpdateBankInfoRequest(String beneficiaryName, String bankName,
                                 String accountNumber, String ibanNumber, String otp) {
        this.bankInfo = new BankInfo(beneficiaryName, bankName, accountNumber, ibanNumber);
        this.paymentInfoOtp = otp;
    }

    public static class BankInfo {
        public String beneficiaryName;
        public String bankName;
        public String accountNumber;
        public String ibanNumber;

        public BankInfo(String beneficiaryName, String bankName,
                        String accountNumber, String ibanNumber) {
            this.beneficiaryName = beneficiaryName;
            this.bankName = bankName;
            this.accountNumber = accountNumber;
            this.ibanNumber = ibanNumber;
        }
    }





}
