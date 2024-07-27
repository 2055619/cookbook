import {IUser, IUserProfile} from "../../assets/models/Authentication";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import Landing from "./Landing";
import {useNavigate} from "react-router-dom";

interface IUserProfileProps {
    user: IUser;
}

function UserProfile({user}: IUserProfileProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const navigate = useNavigate();

    const [userProfile, setUserProfile] = useState<IUserProfile>(
        {
            username: "UserName",
            email: "Email",
            firstName: "FirstName",
            lastName: "LastName",
            solidUnit: "SolidUnit",
            liquidUnit: "LiquidUnit",
            powderUnit: "PowderUnit",
            otherUnit: "OtherUnit"
        }
    );
    const [isFollowing, setIsFollowing] = useState<boolean>(false);
    const [followerCount, setFollowerCount] = useState<number>(0);
    const [followingCount, setFollowingCount] = useState<number>(0);
    const [username, setUsername] = useState<string>("");
    const [tmpUser, setTmpUser] = useState<string | null>("");

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const newUsername = urlParams.get('username');
        
        setTmpUser(newUsername! + Math.random().toString());

        if (newUsername && newUsername !== username) {
            setUsername(newUsername);

            cookbookService.getUserProfile(newUsername)
                .then((response) => {
                    setUserProfile(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }
    }, [tmpUser, username]);

    useEffect(() => {
        updateFollowersAndFollowing();
    }, [username]);

    function updateFollowersAndFollowing() {
        if (!username)
            return;

        cookbookService.getFollowers(username)
            .then((response) => {
                setFollowerCount(response.length);
                setIsFollowing(response.filter(follower => follower.username === user.username).length > 0);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
            });
        cookbookService.getFollowing(username)
            .then((response) => {
                setFollowingCount(response.length);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
            });
    }

    async function handleFollowClick() {
        if (isFollowing) {
            await cookbookService.unfollow(userProfile.username)
                .then((response) => {
                    setIsFollowing(false);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        } else {
            await cookbookService.follow(userProfile.username)
                .then((response) => {
                    setIsFollowing(true);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }

        updateFollowersAndFollowing();
    }

    return (
        <div className={"text-start ms-2"}>

            <div className="grid grid-cols-3 text-center mb-2">
                <span className={"text-3xl"}>{userProfile.firstName} {userProfile.lastName}</span>
                <span className={"text-3xl"}>{userProfile.username}</span>
                <span className={"text-3xl"}>{t('email')}: {userProfile.email}</span>
            </div>

            <div className={"text-center "}>
                {
                    user?.username !== userProfile.username &&
                    <button
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 w-1/6"
                        onClick={handleFollowClick}>
                        {isFollowing ? t('unfollow') : t('follow')}
                    </button>
                }

                <span className={"text-3xl mx-2"}>{followerCount} {t('followers')}</span>
                <span className={"text-3xl mx-2"}>{followingCount} {t('following')}</span>
            </div>


            <h2 className={"text-3xl"}>{t('solidUnit')}: {t(userProfile.solidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('liquidUnit')}: {t(userProfile.liquidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('powderUnit')}: {t(userProfile.powderUnit)}</h2>
            <h2 className={"text-3xl"}>{t('otherUnit')}: {t(userProfile.otherUnit)}</h2>

            {
                user?.username === userProfile.username &&
                <div className={"text-center mt-2"}>
                    <button
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 w-1/6"
                        onClick={() => {
                            navigate("/u/profileModify?username=" + user.username)
                        }}>
                        {t('profileModify')}
                    </button>
                </div>
            }

            <h2 className={"text-7xl text-center mt-4"}>{t('publication')}</h2>

            <Landing username={userProfile.username} user={user!}/>
        </div>
    );
}

export default UserProfile;