import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import * as React from "react";
import { useEffect } from "react"; // 引入 useEffect
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import TextField from "@mui/material/TextField";
import { FormControl, InputLabel, Select, MenuItem } from "@mui/material";
import Button from "@mui/material/Button";
import { useState } from "react";
import $ from "jquery";

export function Profile() {
    const [state, setState] = useState({
        age: 0,
        gender: "?",
        firstName: "?",
        lastName: "?",
        country: "RUSSIA",
        city: "Moscow",
        street: "?",
        phone: "?",
        email: "?",
        password: "?",
    });

    const handleChange = (event) => {
        const name = event.target.name;
        setState({
            ...state,
            [name]: event.target.value,
        });
    };

    // 从后端获取用户信息
    function getAccount(username) {
        $.ajax({
            url: "api/profile",
            method: "GET",
            data: {
                username: username,
            },
            async: false,
            success: function (res) {
                if (res.success) {
                    // 更新状态以显示后端返回的数据
                    setState({
                        firstName: res.firstName,
                        lastName: res.lastName,
                        email: res.email,
                        phone: res.phone,
                        password: res.password,
                        country: res.country || "RUSSIA", // 默认值
                        city: res.city || "Moscow", // 默认值
                        street: res.street || "?", // 默认值
                        age: res.age,
                        gender: res.gender,
                    });
                } else {
                    console.log("Failed to fetch profile data");
                    alert(res.message);
                }
            },
            error: function (err) {
                console.error("Error fetching profile data:", err);
                alert("Failed to fetch profile data. Please try again.");
            },
        });
    }

    // 在组件加载时调用 getAccount
    useEffect(() => {
        const username = window.sessionStorage.getItem("username");
        if (username) {
            getAccount(username);
        } else {
            console.log("No username found in session storage");
            alert("Please log in to view your profile.");
        }
    }, []); // 空依赖数组表示仅在组件挂载时执行

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Box component="form" noValidate sx={{ mt: 3 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                autoComplete="fname"
                                name="firstName"
                                fullWidth
                                id="firstName"
                                label="First Name"
                                value={state.firstName}
                                autoFocus
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                id="lastName"
                                label="Last Name"
                                name="lastName"
                                value={state.lastName}
                                autoComplete="lname"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                id="email"
                                label="Email"
                                name="email"
                                value={state.email}
                                autoComplete="email"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                id="phone"
                                label="Phone"
                                name="phone"
                                value={state.phone}
                                autoComplete="phone"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                value={state.password}
                                autoComplete="new-password"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                name="age"
                                label="Age"
                                id="age"
                                value={state.age}
                                autoComplete="age"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl fullWidth>
                                <InputLabel id="genderLabel">Gender</InputLabel>
                                <Select
                                    native
                                    value={state.gender}
                                    inputProps={{
                                        name: "gender",
                                        id: "gender-native-simple",
                                    }}
                                    labelId="genderLabel"
                                    id="gender"
                                    label="Gender"
                                    onChange={handleChange}
                                >
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </Select>
                            </FormControl>
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        onClick={() => {
                            alert(
                                state.firstName +
                                " " +
                                state.lastName +
                                "\n" +
                                state.password +
                                "\n" +
                                state.age +
                                "\n" +
                                state.email +
                                "\n" +
                                state.phone +
                                "\n" +
                                state.gender
                            );
                            // sendAccount(state.firstName, state.lastName, state.password, state.age, state.phone, state.email, state.gender);
                        }}
                    >
                        Refresh
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}