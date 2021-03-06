import React, { Component } from 'react';
import {Button} from "react-bootstrap";
import alertify from 'alertifyjs';

/**
 * This is a button that will change the logged in user's password in keycloak.
 *
 * @author J.R. Diehl
 * @version 0.1
 */
class ChangePasswordButton extends Component {
    constructor(props) {
        super(props);
        this.state = { response: null };
    }

    /**
     * Checks if the new password is valid. Currently very stupid.
     *
     * @author J.R. Diehl
     * @version 0.1
     * @returns {boolean}
     */
    validPassword() {
        return this.props.newPassword.length >= 6;
    }

    /**
     * Handles the button click. This will verify that the password is valid and
     * then make an API call to the backend to change the user's password.
     *
     * @author J.R. Diehl
     * @version 0.1
     */
    handleClick = () => {
        if (!this.validPassword()) {
            alertify.error('Password must be at least 6 characters long!');
            return;
        }
        fetch('https://tcs.ecs.baylor.edu/ums/userinfo/changePassword/' + this.props.id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.keycloak.token,
            },
            body: this.props.newPassword,
        })
            .then(response => {
                if (response.status === 200) {
                    alertify.success('Password updated.');
                    return response;
                } else {
                    alertify.error('Failed to update password.');
                    return { status: response.status, message: response.statusText }
                }
            })
            .then(res => {
                this.setState((state, props) => ({
                    //response: JSON.stringify(json, null, 2)
                    repsonse: res
                }));
            })
            .catch(err => {
                this.setState((state, props) => ({ response: err.toString() }));
                alertify.error('Failed to update password.');
                console.log(err);
            })
    };

    render() {
        return (
            <div className="ChangePasswordButton">
                <Button variant='primary' onClick={this.handleClick}>Change Password</Button>
            </div>
        );
    }
}

export default ChangePasswordButton;
