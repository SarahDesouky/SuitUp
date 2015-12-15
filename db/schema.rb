# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20151214202227) do

  create_table "comments", force: true do |t|
    t.integer  "owner_id"
    t.integer  "post_id"
    t.string   "text"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "comments", ["owner_id"], name: "index_comments_on_owner_id"
  add_index "comments", ["post_id"], name: "index_comments_on_post_id"

  create_table "followships", force: true do |t|
    t.integer  "follower_id"
    t.integer  "followee_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "followships", ["followee_id"], name: "index_followships_on_followee_id"
  add_index "followships", ["follower_id"], name: "index_followships_on_follower_id"

  create_table "friendships", force: true do |t|
    t.integer  "user_id"
    t.integer  "friend_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "message_threads", force: true do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "messages", force: true do |t|
    t.integer  "owner_id"
    t.string   "text"
    t.integer  "thread_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.boolean  "read",       default: false
  end

  add_index "messages", ["owner_id"], name: "index_messages_on_owner_id"
  add_index "messages", ["thread_id"], name: "index_messages_on_thread_id"

  create_table "messages_receiveds", force: true do |t|
    t.integer  "message_id"
    t.integer  "user_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "messages_receiveds", ["message_id"], name: "index_messages_receiveds_on_message_id"
  add_index "messages_receiveds", ["user_id"], name: "index_messages_receiveds_on_user_id"

  create_table "posts", force: true do |t|
    t.integer  "owner_id"
    t.string   "text"
    t.string   "image_url"
    t.integer  "profile_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "posts", ["owner_id"], name: "index_posts_on_owner_id"
  add_index "posts", ["profile_id"], name: "index_posts_on_profile_id"

  create_table "users", force: true do |t|
    t.string   "fname"
    t.string   "lname"
    t.string   "date_of_birth"
    t.string   "email"
    t.string   "avatar_url"
    t.string   "gender"
    t.string   "country"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "twitter_id"
  end

end
