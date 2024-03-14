import {useTranslation} from "react-i18next";

function Policies() {
    const {t} = useTranslation();
    return (
        <div className={"text-start px-2 pb-20"}>
            <h1 className={"text-7xl text-center"}>{t('pages.policy.title')}</h1>
            <h1 className={"text-4xl mt-1"}>{t('pages.policy.RGPD.title')}</h1>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.intro')}</h1>
            <span>{t('pages.policy.RGPD.introDesc')}</span>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.law25')}</h1>
            <span>{t('pages.policy.RGPD.law25Desc')}</span>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.collect')}</h1>
            <span>{t('pages.policy.RGPD.collectDesc')}</span>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.conformity')}</h1>
            <span>{t('pages.policy.RGPD.conformityDesc')}</span>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.transparency')}</h1>
            <span>{t('pages.policy.RGPD.transparencyDesc')}</span>

            <h1 className={"text-3xl mt-2"}>{t('pages.policy.RGPD.conclusion')}</h1>
            <span>{t('pages.policy.RGPD.conclusionDesc')}</span>

        </div>
    )
}

export default Policies