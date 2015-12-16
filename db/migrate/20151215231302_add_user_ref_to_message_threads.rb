class AddUserRefToMessageThreads < ActiveRecord::Migration
  def change
    add_reference :message_threads, :user1_id, index: true
  end
end
