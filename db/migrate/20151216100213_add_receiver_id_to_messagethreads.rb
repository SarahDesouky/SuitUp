class AddReceiverIdToMessagethreads < ActiveRecord::Migration
  def change
  	    add_reference :message_threads, :receiver, index: true
  end
end
