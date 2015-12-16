class RenameUserInThread < ActiveRecord::Migration
  def change
  	  	rename_column :message_threads, :user1_id, :user_id
  end
end
