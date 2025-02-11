import * as React from "react";
import {
    Container,
    Grid,
    Paper,
    Typography,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    IconButton,
    Box,
    InputLabel,
    MenuItem,
    FormControl,
    Select,
    Snackbar,
    Alert,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import $ from "jquery";

// 样例数据
const exampleHouses = [
    {
        id: 1,
        houseType: "APARTMENTS",
        address: "Shanghai, 5th Avenue",
        rooms: [
            {
                id: 1,
                roomType: "KITCHEN",
                devices: [
                    { id: 1, deviceType: "AIR_CONDITION", manufacture: "Brand A", available: true },
                    { id: 2, deviceType: "LIGHT", manufacture: "Brand B", available: false },
                ],
            },
            {
                id: 2,
                roomType: "LIVING",
                devices: [
                    { id: 3, deviceType: "FAN", manufacture: "Brand C", available: true },
                ],
            },
        ],
    },
    {
        id: 2,
        houseType: "VILLAS",
        address: "Beijing, 7th Avenue",
        rooms: [
            {
                id: 3,
                roomType: "BEDROOM",
                devices: [
                    { id: 4, deviceType: "CAMERA", manufacture: "Brand D", available: true },
                ],
            },
        ],
    },
];

export function Houses() {
    const [houses, setHouses] = React.useState([]);
    const [openDialog, setOpenDialog] = React.useState(false);
    const [dialogType, setDialogType] = React.useState(""); // To differentiate between adding House/Room/Device
    const [newHouse, setNewHouse] = React.useState({ houseType: "", address: "" });
    const [newRoom, setNewRoom] = React.useState({ roomType: "", houseId: null });
    const [newDevice, setNewDevice] = React.useState({ deviceType: "", manufacture: "", roomId: null });
    const [snackBarOpen, setSnackBarOpen] = React.useState(false);

    // 获取房屋数据的API
    const fetchHouses = () => {
        return $.ajax({
            url: '/api/houses',
            method: 'GET',
            data: {
                username: window.sessionStorage.getItem("username")
            },
        });
    };

// 添加房屋、房间、设备的API
    const addHouseAPI = (house) => {
        return $.ajax({
            url: '/api/houses',
            method: 'POST',
            data: JSON.stringify(house),
            contentType: 'application/json',
        });
    };

    const addRoomAPI = (room) => {
        return $.ajax({
            url: `/api/houses/${room.houseId}/rooms`,
            method: 'POST',
            data: JSON.stringify(room),
            contentType: 'application/json',
        });
    };

    const addDeviceAPI = (device) => {
        return $.ajax({
            url: `/api/rooms/${device.roomId}/devices`,
            method: 'POST',
            data: JSON.stringify(device),
            contentType: 'application/json',
        });
    };

    // 获取房屋数据
    React.useEffect(() => {
        fetchHouses().then(data => {
            setHouses(data);
        });
    }, []);

    const handleOpenDialog = (type, houseId = null, roomId = null) => {
        setDialogType(type);
        if (type === "house") {
            setNewHouse({ houseType: "", address: "" });
        } else if (type === "room") {
            setNewRoom({ roomType: "", houseId });
        } else if (type === "device") {
            setNewDevice({ deviceType: "", manufacture: "", roomId });
        }
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    const handleSave = () => {
        if (dialogType === "house") {
            addHouseAPI(newHouse).then((newHouseData) => {
                setHouses([...houses, newHouseData]);
            });
        } else if (dialogType === "room") {
            addRoomAPI(newRoom).then((newRoomData) => {
                const updatedHouses = houses.map((house) => {
                    if (house.id === newRoom.houseId) {
                        house.rooms.push(newRoomData);
                    }
                    return house;
                });
                setHouses(updatedHouses);
            });
        } else if (dialogType === "device") {
            addDeviceAPI(newDevice).then((newDeviceData) => {
                const updatedHouses = houses.map((house) => {
                    if (house.id === newDevice.roomId) {
                        const updatedRooms = house.rooms.map((room) => {
                            if (room.id === newDevice.roomId) {
                                room.devices.push(newDeviceData);
                            }
                            return room;
                        });
                        house.rooms = updatedRooms;
                    }
                    return house;
                });
                setHouses(updatedHouses);
            });
        }
        setSnackBarOpen(true);
        setOpenDialog(false);
    };

    const handleDeleteHouse = (houseId) => {
        $.ajax({
            url: `/api/houses/${houseId}`,
            method: 'DELETE',
        }).then(() => {
            setHouses(houses.filter((house) => house.id !== houseId));
        });
    };

    const handleDeleteRoom = (houseId, roomId) => {
        $.ajax({
            url: `/api/houses/${houseId}/rooms/${roomId}`,
            method: 'DELETE',
        }).then(() => {
            const updatedHouses = houses.map((house) => {
                if (house.id === houseId) {
                    house.rooms = house.rooms.filter((room) => room.id !== roomId);
                }
                return house;
            });
            setHouses(updatedHouses);
        });
    };

    const handleDeleteDevice = (houseId, roomId, deviceId) => {
        $.ajax({
            url: `/api/rooms/${roomId}/devices/${deviceId}`,
            method: 'DELETE',
        }).then(() => {
            const updatedHouses = houses.map((house) => {
                if (house.id === houseId) {
                    const updatedRooms = house.rooms.map((room) => {
                        if (room.id === roomId) {
                            room.devices = room.devices.filter((device) => device.id !== deviceId);
                        }
                        return room;
                    });
                    house.rooms = updatedRooms;
                }
                return house;
            });
            setHouses(updatedHouses);
        });
    };

    return (
        <Container maxWidth="lg" xs={12} md={12} lg={6} sx={{ mt: 4, mb: 4 }}>
            <Button //添加房屋按钮
                variant="contained"
                color="primary"
                sx={{ mb: 2 }}
                onClick={() => handleOpenDialog("house")}
                startIcon={<AddIcon />}
            >
                Add House
            </Button>
            <Grid container spacing={3}>
                {houses.map((house) => ( //房屋信息卡片
                    <Grid item xs={12} md={12} lg={6} key={house.id}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', overflow: 'auto' }}>
                            <Typography variant="h6" gutterBottom>
                                {house.houseType} - {house.address}
                            </Typography>
                            <Button
                                variant="contained"
                                color="primary"
                                sx={{ mb: 2 }}
                                onClick={() => handleOpenDialog("room", house.id)}
                                startIcon={<AddIcon />}
                            >
                                Add Room
                            </Button>
                            {house.rooms.map((room) => ( //房间信息
                                <Box key={room.id} sx={{ mb: 2 }}>
                                    <Typography variant="h6" gutterBottom>
                                        {room.roomType}
                                    </Typography>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        sx={{ mb: 2 }}
                                        onClick={() => handleOpenDialog("device", house.id, room.id)}
                                        startIcon={<AddIcon />}
                                    >
                                        Add Device
                                    </Button>
                                    <Button
                                        variant="contained"
                                        color="secondary"
                                        sx={{ mb: 2 }}
                                        onClick={() => handleDeleteRoom(house.id, room.id)}
                                        startIcon={<DeleteIcon />}
                                    >
                                        Delete Room
                                    </Button>
                                    <Table sx={{ minWidth: 320 }}>
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Device Type</TableCell>
                                                <TableCell>Manufacture</TableCell>
                                                <TableCell>Available</TableCell>
                                                <TableCell>Actions</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {room.devices.map((device) => ( //设备信息
                                                <TableRow key={device.id}>
                                                    <TableCell>{device.deviceType}</TableCell>
                                                    <TableCell>{device.manufacture}</TableCell>
                                                    <TableCell>{device.available ? "Yes" : "No"}</TableCell>
                                                    <TableCell>
                                                        <IconButton
                                                            color="secondary"
                                                            onClick={() => handleDeleteDevice(house.id, room.id, device.id)}
                                                        >
                                                            <DeleteIcon />
                                                        </IconButton>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </Box>
                            ))}
                            <Button
                                variant="contained"
                                color="secondary"
                                sx={{ mb: 2 }}
                                onClick={() => handleDeleteHouse(house.id)}
                                startIcon={<DeleteIcon />}
                            >
                                Delete House
                            </Button>
                        </Paper>

                    </Grid>
                ))}
            </Grid>

            {/* Dialog for adding House, Room, or Device */}
            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>{dialogType === "house" ? "Add House" : dialogType === "room" ? "Add Room" : "Add Device"}</DialogTitle>
                <DialogContent>
                    {dialogType === "house" && (
                        <>
                            <TextField
                                label="House Type"
                                fullWidth
                                value={newHouse.houseType}
                                onChange={(e) => setNewHouse({ ...newHouse, houseType: e.target.value })}
                                sx={{ mb: 2 }}
                            />
                            <TextField
                                label="Address"
                                fullWidth
                                value={newHouse.address}
                                onChange={(e) => setNewHouse({ ...newHouse, address: e.target.value })}
                                sx={{ mb: 2 }}
                            />
                        </>
                    )}
                    {dialogType === "room" && (
                        <>
                            <FormControl fullWidth sx={{ mb: 2 }}>
                                <InputLabel>Room Type</InputLabel>
                                <Select
                                    fullWidth
                                    value={newRoom.roomType}
                                    onChange={(e) => setNewRoom({ ...newRoom, roomType: e.target.value })}
                                >
                                    <MenuItem value="KITCHEN">Kitchen</MenuItem>
                                    <MenuItem value="BEDROOM">Bedroom</MenuItem>
                                    <MenuItem value="BATHROOM">Bathroom</MenuItem>
                                    <MenuItem value="LIVING">Living Room</MenuItem>
                                </Select>
                            </FormControl>
                        </>
                    )}
                    {dialogType === "device" && (
                        <>
                            <FormControl fullWidth sx={{ mb: 2 }}>
                                <InputLabel>Device Type</InputLabel>
                                <Select
                                    fullWidth
                                    value={newDevice.deviceType}
                                    onChange={(e) => setNewDevice({ ...newDevice, deviceType: e.target.value })}
                                >
                                    <MenuItem value="AIR_CONDITION">Air Condition</MenuItem>
                                    <MenuItem value="LIGHT">Light</MenuItem>
                                    <MenuItem value="FAN">Fan</MenuItem>
                                    <MenuItem value="CAMERA">Camera</MenuItem>
                                </Select>
                                <TextField
                                    label="Manufacture"
                                    fullWidth
                                    value={newDevice.manufacture}
                                    onChange={(e) => setNewDevice({ ...newDevice, manufacture: e.target.value })}
                                    sx={{ mb: 2 }}
                                />
                            </FormControl>
                        </>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="secondary">
                        Cancel
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>

            {/* Snackbar for confirmation */}
            <Snackbar
                open={snackBarOpen}
                autoHideDuration={3000}
                onClose={() => setSnackBarOpen(false)}
            >
                <Alert onClose={() => setSnackBarOpen(false)} severity="success">
                    {dialogType === "house" ? "House added!" : dialogType === "room" ? "Room added!" : "Device added!"}
                </Alert>
            </Snackbar>
        </Container>
    );
}
