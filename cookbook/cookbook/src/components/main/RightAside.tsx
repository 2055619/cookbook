import {IUser} from "../../assets/models/Authentication";

interface IRightAside {
    user: IUser | null;
}
function RightAside({user}: IRightAside) {

    // TODO: Implement RightAside by adding the following
    return (
        <div className="w-1/4 text-white text-end me-2 hidden md:block">
            {/*<h1>Right Aside</h1>*/}

        </div>
    );
}

export default RightAside;