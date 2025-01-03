import React from "react";
import {useTranslation} from "react-i18next";
import {toast} from "react-toastify";

function SupportDevs(){
    const {t} = useTranslation();

    function handleCopyEthAddress() {
        navigator.clipboard.writeText(t('ethWallet'))
            .then(() => {
                toast.success(t('message.ethAddressCopied'));
            })
            .catch((error) => {
                console.error('Failed to copy ETH address: ', error);
            });
    }

    function handleCopyBtcAddress() {
        navigator.clipboard.writeText(t('btcWallet'))
            .then(() => {
                toast.success(t('message.btcAddressCopied'));
            })
            .catch((error) => {
                console.error('Failed to copy BTC address: ', error);
            });
    }

    return (
        <div className={"text-cook"}>
            <h1 className={"font-bold"}>{t('supportDevs')} </h1>
            <ul className={"text-start ms-20"}>
                <li>{t('paypal')}: <a href="https://www.paypal.com/paypalme/QuixoticQC" target="_blank"
                                      rel="noreferrer">
                    {t('paypalLink')}</a>
                </li>
            </ul>

            <h1 className={"text-center font-bold"}>{t('cryptoSupport')}:</h1>
            <ul className={"text-start ms-20 "}>
                <li className={"clickable"}
                    onClick={handleCopyEthAddress}>{t('eth')}: <span>{t('ethWallet')}</span>
                </li>
                <li className={"clickable"}
                    onClick={handleCopyBtcAddress}>{t('btc')}: <span>{t('btcWallet')}</span>
                </li>
            </ul>

        </div>
    )
}

export default SupportDevs;