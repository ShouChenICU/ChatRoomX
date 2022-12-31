/*
 Navicat Premium Data Transfer

 Source Server         : ChatRoomX
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 31/12/2022 09:46:29
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for t_members
-- ----------------------------
DROP TABLE IF EXISTS "t_members";
CREATE TABLE "t_members" (
  "uid_" TEXT NOT NULL,
  "room_id_" TEXT NOT NULL,
  "role_" TEXT NOT NULL DEFAULT '',
  "label_" TEXT NOT NULL DEFAULT '',
  "join_instant_" INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY ("uid_", "room_id_")
);

-- ----------------------------
-- Table structure for t_messages
-- ----------------------------
DROP TABLE IF EXISTS "t_messages";
CREATE TABLE "t_messages" (
  "id_" INTEGER NOT NULL,
  "room_id_" TEXT NOT NULL,
  "uid_" TEXT NOT NULL,
  "type_" TEXT NOT NULL DEFAULT '',
  "content_" TEXT NOT NULL DEFAULT '',
  "instant_" INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY ("id_")
);

-- ----------------------------
-- Table structure for t_rooms
-- ----------------------------
DROP TABLE IF EXISTS "t_rooms";
CREATE TABLE "t_rooms" (
  "id_" TEXT NOT NULL,
  "title_" TEXT NOT NULL DEFAULT '',
  "introduction_" TEXT NOT NULL DEFAULT '',
  "is_public_" INTEGER NOT NULL DEFAULT 0,
  "create_instant_" INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY ("id_")
);

-- ----------------------------
-- Table structure for t_users
-- ----------------------------
DROP TABLE IF EXISTS "t_users";
CREATE TABLE "t_users" (
  "uid_" TEXT NOT NULL,
  "email_" TEXT NOT NULL DEFAULT '',
  "nickname_" TEXT NOT NULL DEFAULT '',
  "password_" TEXT NOT NULL DEFAULT '',
  "gender_" TEXT NOT NULL DEFAULT '',
  "role_" TEXT NOT NULL DEFAULT '',
  "signature_" TEXT NOT NULL DEFAULT '',
  "status_" TEXT NOT NULL DEFAULT '',
  "create_instant_" INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY ("uid_")
);

PRAGMA foreign_keys = true;
